package dev.anaes.qrh

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.ThreadContextElement
import org.w3c.dom.Text

class SwipeItemStandard() : Fragment() {

    companion object {
        fun newInstance(passedStep: String, passedHead: String, passedBody: String, passedType: Int): Fragment {
            val args = Bundle()
            args.putString("step", passedStep)
            args.putString("head", passedHead)
            args.putString("body", passedBody)
            args.putInt("type", passedType)
            val fragment = SwipeItemStandard()
            fragment.arguments = args
            return fragment
        }
    }
    private var step: String = String()
    private var head: String = String()
    private var body: String = String()
    private var type: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            step = it.getString("step").toString()
            head = it.getString("head").toString()
            body = it.getString("body").toString()
            type = it.getInt("type")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return when(type) {
            5,6,7,8,9 -> inflater.inflate(R.layout.swipe_item_standard, container, false)
            else -> inflater.inflate(R.layout.swipe_item_standard, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var detailStart: TextView = view.findViewById(R.id.detail_start)
        var detailCard: MaterialCardView = view.findViewById(R.id.detail_card)
        val detailStep: TextView = view.findViewById(R.id.detail_step)
        val detailHead: TextView = view.findViewById(R.id.detail_head)
        var detailBody: TextView = view.findViewById(R.id.detail_body)


        when (type) {
            1 -> {
                detailStep.visibility = View.GONE
                detailHead.visibility = View.GONE
            }
            2 -> {
                detailCard.visibility = View.GONE
                detailStart.visibility = View.VISIBLE
                detailStart.text = body?.let { htmlProcess(it, view) }
            }
            3 -> {
                detailStep.text = step
                detailHead.text = head
            }
            4 -> {
                detailStep.text = step
                detailHead.visibility = View.GONE
            }
        }

        detailBody.text = body?.let { htmlProcess(it, view) }
    }
}