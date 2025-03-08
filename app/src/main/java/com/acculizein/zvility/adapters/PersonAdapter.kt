import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.acculizein.zvility.R
import com.acculizein.zvility.databinding.ItemExclusiveViewholderBinding
import com.acculizein.zvility.models.Person
import com.acculizein.zvility.screens.PersonDetailsActivity

class PersonAdapter(private val personList: List<Person>) :
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    class PersonViewHolder(val binding: ItemExclusiveViewholderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = ItemExclusiveViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = personList[position]
        val context = holder.itemView.context
        with(holder.binding) {
            ivPerson.setImageResource(person.imageResId)
            tvPersonName.text = person.name
            tvCategory.text = person.category
            tvLocation.text = person.location
            itemStatus.text = person.status

            // Change text color based on status
            val statusColor = if (person.status.equals("Open", ignoreCase = true)) {
                ContextCompat.getColor(context, R.color.green)
            } else {
                ContextCompat.getColor(context, R.color.red)
            }
            itemStatus.setTextColor(statusColor)

            // Handle Save/Unsave Click
            tvBtnSave.setOnClickListener {
                val isSaved = tvBtnSave.text.toString() == "Saved"
                if(isSaved){
                    tvBtnSave.text = "Save"
                    tvBtnSave.setTextColor(ContextCompat.getColor(context, R.color.dark_gray))
                    tvBtnSave.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark, 0, 0, 0)
                }else{
                    tvBtnSave.text = "Saved"
                    tvBtnSave.setTextColor(ContextCompat.getColor(context, R.color.blue))
                    tvBtnSave.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_filled_blue, 0, 0, 0)
                }
            }

            // Handle item click to open details activity
            root.setOnClickListener {
                val intent = Intent(context, PersonDetailsActivity::class.java)
                intent.putExtra("person", person)
                context.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int = personList.size
}
