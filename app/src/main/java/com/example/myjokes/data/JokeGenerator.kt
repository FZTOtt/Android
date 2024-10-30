package com.example.myjokes.data

import kotlin.random.Random

class JokeGenerator {
    fun generateJokeData(): List<Joke> {
        return buildList {
            add(generateTestJoke(1))
            for (i in 2..7) {
                add(generateRandomJoke(i))
            }
        }
    }

    private fun generateRandomJoke(index: Int): Joke {
        return Joke(
            id = index,
            category = "Category $index",
            question = "Question $index",
            answer = "Answer $index"
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
                    "им виднее, так что ограничимся одной..."
        )
    }
}