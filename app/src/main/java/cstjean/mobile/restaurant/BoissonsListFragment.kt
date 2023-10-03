package cstjean.mobile.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cstjean.mobile.restaurant.databinding.FragmentBoissonsListBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

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

        binding.boissonsRecyclerView.layoutManager = LinearLayoutManager(context)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val boissons = boissonsListViewModel.loadBoissons()
                binding.boissonsRecyclerView.adapter = BoissonsListAdapter(boissons)
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
