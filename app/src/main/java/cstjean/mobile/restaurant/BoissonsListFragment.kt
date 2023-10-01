package cstjean.mobile.restaurant

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cstjean.mobile.restaurant.databinding.FragmentTravauxListBinding

private const val TAG = "TravauxListFragment"

/**
 * Fragment pour la liste des travaux.
 *
 * @author Gabriel T. St-Hilaire
 */
class BoissonsListFragment : Fragment() {
    private var _binding: FragmentTravauxListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }

    private val boissonsListViewModel: BoissonsListViewModel by viewModels()

    /**
     * Initialisation du Fragment.
     *
     * @param savedInstanceState Les données conservées au changement d'état.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Boissons : ${boissonsListViewModel.boissons.size}")
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTravauxListBinding.inflate(inflater, container, false)

        binding.travauxRecyclerView.layoutManager = LinearLayoutManager(context)

        val boissons = boissonsListViewModel.boissons
        val adapter = TravauxListAdapter(boissons)
        binding.travauxRecyclerView.adapter = adapter

        return binding.root
    }

    /**
     * Lorsque la vue est détruite.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
