package cstjean.mobile.restaurant

import android.app.AlertDialog
import android.R as U
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.doOnLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import cstjean.mobile.restaurant.databinding.FragmentBoissonBinding
import cstjean.mobile.restaurant.boisson.Boisson
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date
import androidx.navigation.fragment.findNavController




/**
 * Fragment pour la gestion d'une boisson.
 *
 * @author Raphael ostiguy & Yong Jian Qiu
 */
class BoissonFragment : Fragment() {
    private var _binding: FragmentBoissonBinding? = null

    private val boissonRepository = BoissonRepository.get()
    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }

    /**
     * Vérifie si l'intent peut être résolu.
     */
    private fun canResolveIntent(intent: Intent): Boolean {
        val packageManager: PackageManager = requireActivity().packageManager
        return intent.resolveActivity(packageManager) != null
    }

    /**
     * Lorsque la photo est prise l'ascien est effacer et le nouveau est sauvegarder.
     */
    private val prendrePhoto =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { photoPrise: Boolean ->
            if (photoPrise && photoFilename != null) {
                boissonViewModel.updateBoisson { oldBoisson ->

                    deletePhoto(oldBoisson.photoFilename)
                    oldBoisson.copy(photoFilename = photoFilename)
                }
            }
        }
    private var photoFilename: String? = null

    private val args: BoissonFragmentArgs by navArgs()
    private val boissonViewModel: BoissonViewModel by viewModels() {
        BoissonViewModelFactory(args.boissonId)
    }


    /**
     * Instanciation de l'interface.
     *
     * @param inflater Pour instancier l'interface.
     * @param container Le parent qui contiendra notre interface.
     * @param savedInstanceState Les données conservées au changement d'état.
     *
     * @return La vue instanciée.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoissonBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**
     * Lorsque la vue est créée.
     *
     * @param view La vue créée.
     * @param savedInstanceState Les données conservées au changement d'état.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                boissonViewModel.boisson.collect { boisson ->
                    boisson?.let { updateUi(it) }
                }
            }
        }
        binding.apply {

            val items = Produit.values().map { it.toString() }.toTypedArray()
            val adapter = ArrayAdapter(requireContext(), U.layout.simple_spinner_dropdown_item, items)

            binding.typeBoisson.adapter = adapter

            binding.typeBoisson.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedType = items[position]
                    boissonViewModel.updateBoisson { oldCarteFidelite ->
                        oldCarteFidelite.copy(typeProduit = Produit.valueOf(selectedType))
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            val cameraIntent = prendrePhoto.contract.createIntent(
                requireContext(),
                Uri.parse("")
            )
            // cameraIntent.addCategory(Intent.CATEGORY_APP_CALCULATOR) // Pour tester !
            boissonCamera.isEnabled = canResolveIntent(cameraIntent)

            boissonCamera.setOnClickListener {
                photoFilename = "IMG_${Date()}.JPG"
                val photoFichier = File(requireContext().applicationContext.filesDir, photoFilename)
                val photoUri = FileProvider.getUriForFile(
                    requireContext(),
                    "cstjean.mobile.fileprovider",
                    photoFichier
                )
                prendrePhoto.launch(photoUri)
            }

            boissonNom.doOnTextChanged { text, _, _, _ ->
                boissonViewModel.updateBoisson { oldBoisson ->
                    oldBoisson.copy(nom = text.toString())
                }
            }

            boissonPays.doOnTextChanged { text, _, _, _ ->
                boissonViewModel.updateBoisson { oldBoisson ->
                    oldBoisson.copy(paysOrigin = text.toString())
                }
            }

            boissonProducteur.doOnTextChanged { text, _, _, _ ->
                boissonViewModel.updateBoisson { oldBoisson ->
                    oldBoisson.copy(producteur = text.toString())
                }
            }


        }
    }

    /**
     * Supprime la photo sur l'appareil.
     */
    private fun deletePhoto(photoFilename: String?) {
        if (photoFilename != null) {
            val photoFichier = File(
                requireContext().applicationContext.filesDir,
                photoFilename
            )
            photoFichier.delete()
        }
    }

    /**
     * Modal de validation pour supprimer.
     */
    private fun showDeleteConfirmationDialog(photoFilename: String?, boisson: Boisson) {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle("Confirmation")
            setMessage("Voulez-vous vraiment supprimer cet élément ?")
            setPositiveButton("Oui") { _, _ ->

                deletePhoto(photoFilename)
                viewLifecycleOwner.lifecycleScope.launch {
                    deleteBoisson(boisson)
                }
                findNavController().popBackStack()
            }
            setNegativeButton("Non", null)
        }
        builder.create().show()
    }

    /**
     *  Supprime la boisson en bd.
     */
    suspend fun deleteBoisson(boisson: Boisson) {
        boissonRepository.deleteBoisson(boisson)
    }

    /**
     * Met à jour la photo.
     */
    private fun updatePhoto(photoFilename: String?) {

        if (binding.boissonPhoto.tag != photoFilename) {

            val photoFichier = photoFilename?.let {
                File(requireContext().applicationContext.filesDir, it)
            }
            if (photoFichier?.exists() == true) {
                binding.boissonPhoto.doOnLayout { view ->
                    val scaledBitmap = getScaledBitmap(
                        photoFichier.path,
                        view.width,
                        view.height
                    )
                    binding.boissonPhoto.setImageBitmap(scaledBitmap)
                    binding.boissonPhoto.scaleType = ImageView.ScaleType.FIT_XY
                    binding.boissonPhoto.tag = photoFilename


                }
            }
        }
        else {
            binding.boissonPhoto.setImageResource(R.drawable.photo)
            binding.boissonPhoto.scaleType = ImageView.ScaleType.FIT_XY
            binding.boissonPhoto.tag = "default"
        }
    }

    /**
     * Met à jour l'interface.
     */
    private fun updateUi(boisson: Boisson) {
        binding.apply {

            if (boisson.photoFilename != null) {
                binding.btnPartager.visibility = View.VISIBLE
                binding.btnPartager.setOnClickListener {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        val sujetMessage = getString(R.string.boisson_partager, boisson.nom)
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "test")
                        putExtra(Intent.EXTRA_SUBJECT, sujetMessage)
                    }

                    startActivity(intent)
                }
            }


            binding.btnSupprimer.setOnClickListener {
                showDeleteConfirmationDialog(photoFilename, boisson)
            }

            if (boissonNom.text.toString() != boisson.nom) {
                boissonNom.setText(boisson.nom)
            }

            if (boissonPays.text.toString() != boisson.paysOrigin) {
                boissonPays.setText(boisson.paysOrigin)
            }
            if (boissonProducteur.text.toString() != boisson.producteur) {
                boissonProducteur.setText(boisson.producteur)
            }

            updatePhoto(boisson.photoFilename)

            val items = Produit.values().map { it.toString() }.toTypedArray()
            val currentTypeProduit = boisson.typeProduit.toString()

            val defaultPosition = items.indexOf(currentTypeProduit)

            typeBoisson.setSelection(defaultPosition)
        }
    }

    /**
     * Lorsque la vue est détruite.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}