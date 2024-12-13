package com.example.myjokes.data

import com.example.myjokes.domain.entity.Joke

object JokeGenerator {

    val data = mutableListOf<Joke>()

    fun generateJokeData(): List<Joke> {
        data.clear()
        data.addAll(buildList {
            add(generateTestJoke(1))
            for (i in 2..7) {
                add(generateRandomJoke(i))
            }
        })
        return data
    }

    private fun generateRandomJoke(index: Int): Joke {
        return Joke(
            id = index,
            category = "Category $index",
            question = "Question $index",
            answer = "Answer $index",
            own = true
        )
    }

    private fun generateTestJoke(index: Int): Joke {
        return Joke(
            id = index,
            category = "Это шутка для теста требования к ограничению длины ответа",
            question = "Сколько максимум строк должно быть в ответе?",
            answer = "Это очень хороший вопрос, ответ в конце. Начать стоит с того, а сколько строк должен " +
                    "увидеть пользователь? На мой взгляд одной строки недостаточно, но это решаем не мы," +
                    "пчёлки-прогеры, а дяди в пиджачках, у них вон на днях UI/UX исследование прошло," +
                    "им виднее, так что ограничимся одной...",
            own = true
        )
    }
}