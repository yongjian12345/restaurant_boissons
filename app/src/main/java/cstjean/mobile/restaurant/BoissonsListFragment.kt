package cstjean.mobile.restaurant

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cstjean.mobile.restaurant.databinding.FragmentBoissonsListBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cstjean.mobile.restaurant.boisson.Boisson
import kotlinx.coroutines.launch
import java.util.UUID

private const val TAG = "BoissonsListFragment"

/**
 * Fragment pour la liste des travaux.
 *
 * @author Gabriel T. St-Hilaire
 */
class BoissonsListFragment : Fragment() {
    private var _binding: FragmentBoissonsListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }

    private val boissonsListViewModel: BoissonsListViewModel by viewModels()


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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoissonsListBinding.inflate(inflater, container, false)

        
        binding.boissonsRecyclerView.layoutManager = GridLayoutManager(context, 2)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                boissonsListViewModel.boissons.collect { boissons ->
                    val adapter = BoissonsListAdapter(boissons) { boissonId ->

                        findNavController().navigate(
                            BoissonsListFragmentDirections.showBoissonDetail(
                                boissonId
                            )
                        )

                    }

                    val searchEditText = view.findViewById<EditText>(R.id.recherche)
                    searchEditText.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

                        override fun afterTextChanged(s: Editable) {
                            adapter.filter(s.toString())
                        }
                    })

                    val currentText = searchEditText.text.toString()
                    adapter.filter(currentText)

                    binding.boissonsRecyclerView.adapter = adapter
                }
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_boissons_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.nouveau_travail -> {
                        viewLifecycleOwner.lifecycleScope.launch {
                            val nouvelleBoisson = Boisson(
                                UUID.randomUUID(),
                                "boisson",
                                Produit.Vin,
                                "Canada",
                                "Moi",
                                null
                            )
                            boissonsListViewModel.addBoisson(nouvelleBoisson)
                            findNavController().navigate(
                                BoissonsListFragmentDirections.showBoissonDetail(nouvelleBoisson.id)
                            )
                        }
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)



    }

    /**
     * Lorsque la vue est détruite.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
