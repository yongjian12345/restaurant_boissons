package cstjean.mobile.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import java.util.Date
import java.util.UUID

/**
 * Fragment pour la gestion d'un travail.
 *
 * @author Gabriel T. St-Hilaire
 */
class BoissonFragment : Fragment() {
    private var _binding: FragmentBoissonBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }

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
    ): View? {
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

            boissonNom.doOnTextChanged { text, _, _, _ ->
                boissonViewModel.updateBoisson { oldBoisson ->
                    oldBoisson.copy(nom = text.toString())
                }
            }

             boissonType.doOnTextChanged { text, _, _, _ ->
                boissonViewModel.updateBoisson { boissonType ->
                    boissonType.copy(typeProduit = Produit.fromNom(boissonType.toString()))
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

            boissonImage.doOnTextChanged { text, _, _, _ ->
                boissonViewModel.updateBoisson { oldBoisson ->
                    oldBoisson.copy(photoFilename = text.toString())
                }
            }

        }
    }

    private fun updateUi(boisson: Boisson) {
        binding.apply {
// Pour éviter une loop infinie avec le update
            if (boissonNom.text.toString() != boisson.nom) {
                boissonNom.setText(boisson.nom)
            }
            if (boissonType.text.toString() != boisson.typeProduit.toString()) {
                boissonType.setText(boisson.typeProduit.nom)
            }
            if (boissonPays.text.toString() != boisson.paysOrigin) {
                boissonPays.setText(boisson.paysOrigin)
            }
            if (boissonProducteur.text.toString() != boisson.producteur) {
                boissonProducteur.setText(boisson.producteur)
            }
            if (boissonImage.text.toString() != boisson.photoFilename) {
                boissonImage.setText(boisson.photoFilename)
            }



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