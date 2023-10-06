package cstjean.mobile.restaurant

import android.widget.ImageView

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnLayout
import androidx.recyclerview.widget.RecyclerView
import cstjean.mobile.restaurant.databinding.ListItemBoissonBinding
import cstjean.mobile.restaurant.boisson.Boisson
import cstjean.mobile.restaurant.boisson.TypeBoisson
import java.io.File
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

        binding.boissonPaysOrigine.text = boisson.paysOrigin
        binding.boissonProducteur.text = boisson.producteur

        when (boisson.typeProduit) {
            Produit.Vin -> binding.typeBoisson.setImageResource(R.drawable.vin)
            Produit.Spiritueux -> binding.typeBoisson.setImageResource(R.drawable.spiritueux)
            Produit.Aperitif -> binding.typeBoisson.setImageResource(R.drawable.aperitif)
            Produit.Biere -> binding.typeBoisson.setImageResource(R.drawable.biere)
            Produit.Autre -> binding.typeBoisson.setImageResource(R.drawable.autre)
        }

        binding.root.setOnClickListener {
            onBoissonClicked(boisson.id)
        }

        val context = itemView.context
        val photoFichier = boisson.photoFilename?.let {
            File(context.filesDir, it)
        }
        if (binding.boissonPhoto.tag != boisson.photoFilename) {
            if (photoFichier?.exists() == true) {
                binding.boissonPhoto.doOnLayout { view ->
                    val scaledBitmap = getScaledBitmap(
                        photoFichier.path,
                        view.width,
                        view.height
                    )
                    binding.boissonPhoto.setImageBitmap(scaledBitmap)
                    binding.boissonPhoto.scaleType = ImageView.ScaleType.FIT_XY
                    binding.boissonPhoto.tag = boisson.photoFilename
                }
            }
        } else {
            binding.boissonPhoto.setImageResource(R.drawable.photo)

            binding.boissonPhoto.tag = "default"
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


    private var filteredBoissons: List<Boisson> = boissons

    fun filter(query: String) {
        filteredBoissons = if (query.isEmpty()) {
            boissons
        } else {
            boissons.filter {
                it.nom.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }


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
        val boisson = filteredBoissons[position]
        holder.bind(boisson, onBoissonClicked)
    }


    /**
     * Récupère le nombre total d'item de notre liste.
     *
     * @return Le nombre d'item total de notre liste.
     */
    override fun getItemCount() = filteredBoissons.size
}
