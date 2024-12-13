

import com.example.myjokes.data_clean.entity.JokeDTO
import com.example.myjokes.domain.entity.Joke
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JokesApiResponse(
    @SerialName("error")
    val error: Boolean,
    @SerialName("amount")
    val amount: Int,
    @SerialName("jokes")
    val jokes: List<JokeDTO>
)