package cstjean.mobile.ecole

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import cstjean.mobile.ecole.databinding.FragmentTravailBinding
import cstjean.mobile.ecole.travail.Travail
import java.util.Date
import java.util.UUID

/**
 * Fragment pour la gestion d'un travail.
 *
 * @author Gabriel T. St-Hilaire
 */
class TravailFragment : Fragment() {
    private var _binding: FragmentTravailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }

    private lateinit var travail: Travail

    /**
     * Initialisation du Fragment.
     *
     * @param savedInstanceState Les données conservées au changement d'état.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        travail = Travail(UUID.randomUUID(), "Travail 1", Date(),false)
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
        _binding = FragmentTravailBinding.inflate(inflater, container, false)

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

        binding.apply {
            travailNom.doOnTextChanged { text, _, _, _ ->
                travail = travail.copy(nom = text.toString())
            }

            travailTermine.setOnCheckedChangeListener { _, isChecked ->
                travail = travail.copy(estTermine = isChecked)
            }

            travailDate.apply {
                text = travail.dateRemise.toString()
                isEnabled = false
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