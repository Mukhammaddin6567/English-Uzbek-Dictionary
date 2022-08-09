package uz.gita.dictionaryAppMVVM.presenter.adapter

import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import uz.gita.dictionaryAppMVVM.R
import uz.gita.dictionaryAppMVVM.databinding.ItemWordBinding
import uz.gita.dictionaryAppMVVM.utils.onClick
import uz.gita.dictionaryAppMVVM.utils.paintResult

class DictionaryCursorAdapter : RecyclerView.Adapter<DictionaryCursorAdapter.Holder>() {

    private var onClickFavouriteListener: ((Int, Int) -> Unit)? = null
    private var onClickItemListener: ((Int) -> Unit)? = null

//    private var statesList = LinkedHashMap<Int, Boolean>()

    var cursor: Cursor? = null
    var query: String? = null

    inner class Holder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.onClick {
                Timber.d("itemView onClick")
                cursor?.let {
                    it.moveToPosition(absoluteAdapterPosition)
                    Timber.d("clicked item id: ${it.getInt(0)}")
                    onClickItemListener?.invoke(it.getInt(0))
                }
            }
            binding.isFavourite.onClick {
                cursor?.let {
                    it.moveToPosition(absoluteAdapterPosition)
                    onClickFavouriteListener?.invoke(it.getInt(4), it.getInt(0))
                }
            }
        }

        fun bind() = with(binding) {
            cursor?.let { cursor ->
                cursor.moveToPosition(absoluteAdapterPosition)
                textWord.text = when (query) {
                    null -> cursor.getString(1)
                    else -> query?.let { cursor.getString(1).paintResult(it) }
                }
                textType.text =
                    itemView.resources.getString(R.string.text_item_type, cursor.getString(2))
                textTranslate.text = cursor.getString(3)
//                textTranslate.text = when (query) {
//                    null -> cursor.getString(3)
//                    else -> query?.let { cursor.getString(3).paintResult(it) }
//                }
                when (cursor.getInt(4)) {
                    0 -> isFavourite.setImageResource(R.drawable.ic_star_off)
                    else -> isFavourite.setImageResource(R.drawable.ic_star_on)
                }

                /*when {
                    statesList.containsKey(absoluteAdapterPosition) -> {
                        isFavourite.isChecked = statesList.getValue(absoluteAdapterPosition)
                    }
                    else -> isFavourite.isChecked = when (cursor.getInt(4)) {
                        0 -> false
                        else -> true
                    }
                }*/
//                Timber.d("$absoluteAdapterPosition: ${isFavourite.isChecked}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return Holder(ItemWordBinding.bind(view))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
//        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim)
//        holder.itemView.startAnimation(animation)
        holder.bind()
    }

    override fun getItemCount(): Int {
//        Timber.d(cursor?.count.toString())
        cursor?.let { return it.count }
        return 0
    }

    fun setOnClickFavouriteListener(block: (Int, Int) -> Unit) {
        onClickFavouriteListener = block
    }

    fun setOnItemClickListener(block: (Int) -> Unit) {
        onClickItemListener = block
    }

}