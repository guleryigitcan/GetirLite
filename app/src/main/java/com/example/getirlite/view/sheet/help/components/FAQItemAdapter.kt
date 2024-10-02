package com.example.getirlite.view.sheet.help.components

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.example.getirlite.databinding.CellFaqItemBinding
import com.example.getirlite.view.sheet.help.model.FAQItem

class FAQItemAdapter(private val items: List<FAQItem>): RecyclerView.Adapter<FAQItemAdapter.FAQItemViewHolder>() {

    inner class FAQItemViewHolder(private val binding: CellFaqItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var expanded = false

        fun setData(item: FAQItem, isLast: Boolean) {
            binding.divider.visibility = if (isLast) View.GONE else View.VISIBLE
            binding.labelQuestion.text = item.question
            binding.labelAnswer.text = item.answer
            binding.main.setOnClickListener {
                expanded = !expanded
                animateExpand(expanded)
            }
        }

        private fun animateExpand(expand: Boolean) {
            val animatorRotation = ValueAnimator.ofFloat(binding.iconExpand.rotation,
                (if (expand) 180f else 0f)
            )
            animatorRotation.setDuration(300)
            animatorRotation.interpolator = AccelerateDecelerateInterpolator()
            animatorRotation.addUpdateListener { progress: ValueAnimator ->
                val value = progress.animatedValue as Float
                try { binding.iconExpand.rotation = value } catch (e: Exception) { animatorRotation.cancel() }
            }

            animatorRotation.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
                override fun onAnimationCancel(animation: Animator) { try { animation.removeAllListeners() } catch (_: Exception) { } }
                override fun onAnimationEnd(animation: Animator) { try { animation.removeAllListeners() } catch (_: Exception) { } }
            })


            if (expand) {
                binding.labelAnswer.visibility = View.VISIBLE
                binding.labelAnswer.measure(
                    View.MeasureSpec.makeMeasureSpec(binding.root.width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                val targetHeight = binding.labelAnswer.measuredHeight + 50

                val heightAnimator = ValueAnimator.ofInt(binding.labelAnswer.height, targetHeight)
                heightAnimator.duration = 300
                heightAnimator.interpolator = AccelerateDecelerateInterpolator()
                heightAnimator.addUpdateListener { animator ->
                    binding.labelAnswer.layoutParams.height = animator.animatedValue as Int
                    binding.labelAnswer.requestLayout()
                }
                heightAnimator.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                    override fun onAnimationCancel(animation: Animator) { try { animation.removeAllListeners() } catch (_: Exception) { } }
                    override fun onAnimationEnd(animation: Animator) { try { animation.removeAllListeners() } catch (_: Exception) { } } })

                heightAnimator.start()
            }
            else {
                val heightAnimator = ValueAnimator.ofInt(binding.labelAnswer.height, 0)
                heightAnimator.duration = 300
                heightAnimator.interpolator = AccelerateDecelerateInterpolator()
                heightAnimator.addUpdateListener { animator ->
                    binding.labelAnswer.layoutParams.height = animator.animatedValue as Int
                    binding.labelAnswer.requestLayout()
                }
                heightAnimator.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                    override fun onAnimationCancel(animation: Animator) { try { animation.removeAllListeners() } catch (_: Exception) { } }
                    override fun onAnimationEnd(animation: Animator) {
                        try {
                            binding.labelAnswer.visibility = View.GONE
                            animation.removeAllListeners()
                    } catch (_: Exception) { }
                    }
                })
                heightAnimator.start()
            }

            animatorRotation.start()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQItemViewHolder = FAQItemViewHolder(
        CellFaqItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: FAQItemViewHolder, position: Int) = holder.setData(item = items[position], isLast = position == itemCount -1)

    override fun getItemCount(): Int = items.size
}