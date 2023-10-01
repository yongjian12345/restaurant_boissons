package cstjean.mobile.ecole

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import cstjean.mobile.ecole.databinding.ListItemTravailBinding
import cstjean.mobile.ecole.boisson.Boisson

/**
 * ViewHolder pour notre RecyclerView de travaux.
 *
 * @property binding Binding de la vue pour une cellule.
 *
 * @author Gabriel T. St-Hilaire
 */
class TravailHolder(private val binding: ListItemTravailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * On associe un travail à ce ViewHolder.
     *
     * @param boisson Le travail associé.
     */
    fun bind(boisson: Boisson) {
        binding.travailNom.text = boisson.nom
        binding.travailDate.text = boisson.dateRemise.toString()
        binding.travailTermine.visibility = if (boisson.estTermine) View.VISIBLE else View.GONE

        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context, boisson.nom, Toast.LENGTH_SHORT)
                .show()
        }
    }
}

/**
 * Adapter pour notre RecyclerView de travaux.
 *
 * @property boissons Liste des travaux à afficher.
 *
 * @author Gabriel T. St-Hilaire
 */
class TravauxListAdapter(private val boissons: List<Boisson>) :
    RecyclerView.Adapter<TravailHolder>() {

    /**
     * Lors de la création des ViewHolder.
     *
     * @param parent Layout dans lequel la nouvelle vue
     *                 sera ajoutée quand elle sera liée à une position.
     * @param viewType Le type de vue de la nouvelle vue.
     *
     * @return Un ViewHolder pour notre cellule.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravailHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTravailBinding.inflate(inflater, parent, false)
        return TravailHolder(binding)
    }

    /**
     * Associe un élément à un ViewHolder.
     *
     * @param holder Le ViewHolder à utiliser.
     * @param position La position dans la liste qu'on souhaite utiliser.
     */
    override fun onBindViewHolder(holder: TravailHolder, position: Int) {
        val boisson = boissons[position]
        holder.bind(boisson)
    }

    /**
     * Récupère le nombre total d'item de notre liste.
     *
     * @return Le nombre d'item total de notre liste.
     */
    override fun getItemCount() = boissons.size
}
