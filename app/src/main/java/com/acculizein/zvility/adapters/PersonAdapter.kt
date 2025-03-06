import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.acculizein.zvility.R
import com.acculizein.zvility.databinding.ItemExclusiveViewholderBinding
import com.acculizein.zvility.models.Person

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
        }
    }

    override fun getItemCount(): Int = personList.size
}
