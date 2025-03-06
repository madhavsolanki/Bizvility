package com.acculizein.zvility.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acculizein.zvility.databinding.ItemReviewsViewholderBinding
import com.acculizein.zvility.models.Review

class ReviewAdapter(
    private val reviewList: List<Review>,
    private val onEditClick: (Review) -> Unit,
    private val onDeleteClick: (Review) -> Unit
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(val binding: ItemReviewsViewholderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewsViewholderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewList[position]

        holder.binding.tvLoggedInUser.text = review.loggedInUser
        holder.binding.tvVerifiedUser.text = review.verifiedUser
        holder.binding.tvReviewRating.text = review.rating.toString()
        holder.binding.tvReviewDate.text = review.reviewDate
        holder.binding.tvReview.text = review.reviewText

        // Handle Edit & Delete Clicks
//        holder.binding.btnEditReview.setOnClickListener { onEditClick(review) }
//        holder.binding.btnDeleteReview.setOnClickListener { onDeleteClick(review) }
    }

    override fun getItemCount(): Int = reviewList.size
}
