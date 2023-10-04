package cstjean.mobile.restaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import cstjean.mobile.restaurant.databinding.ListItemBoissonBinding
import cstjean.mobile.restaurant.boisson.Boisson
import java.util.UUID

/**
 * ViewHolder pour notre RecyclerView de travaux.
 *
 * @property binding Binding de la vue pour une cellule.
 *
 * @author Gabriel T. St-Hilaire
 */
class BoissonHolder(private val binding: ListItemBoissonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * On associe un travail à ce ViewHolder.
     *
     * @param boisson Le travail associé.
     */
    fun bind(boisson: Boisson, onBoissonClicked: (boissonId : UUID) -> Unit) {
        binding.boissonNom.text = boisson.nom
        binding.boinssonTypeProduit.text = boisson.typeProduit.nom
        binding.boissonPaysOrigine.text = boisson.paysOrigin
        binding.boissonProducteur.text = boisson.producteur
        binding.boissonPhoto.text = boisson.photoFilename

        binding.boissonPhoto.visibility = if (boisson.photoFilename == null) View.GONE else View.VISIBLE

        binding.root.setOnClickListener {
            onBoissonClicked(boisson.id)
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
class BoissonsListAdapter(
    private val boissons: List<Boisson>,
    private val onBoissonClicked: (boissonId : UUID) -> Unit) :
    RecyclerView.Adapter<BoissonHolder>() {

    /**
     * Lors de la création des ViewHolder.
     *
     * @param parent Layout dans lequel la nouvelle vue
     *                 sera ajoutée quand elle sera liée à une position.
     * @param viewType Le type de vue de la nouvelle vue.
     *
     * @return Un ViewHolder pour notre cellule.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoissonHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBoissonBinding.inflate(inflater, parent, false)
        return BoissonHolder(binding)
    }

    /**
     * Associe un élément à un ViewHolder.
     *
     * @param holder Le ViewHolder à utiliser.
     * @param position La position dans la liste qu'on souhaite utiliser.
     */
    override fun onBindViewHolder(holder: BoissonHolder, position: Int) {
        val boisson = boissons[position]
        holder.bind(boisson, onBoissonClicked)
    }

    /**
     * Récupère le nombre total d'item de notre liste.
     *
     * @return Le nombre d'item total de notre liste.
     */
    override fun getItemCount() = boissons.size
}
