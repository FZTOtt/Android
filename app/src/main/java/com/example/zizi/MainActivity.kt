package com.example.zizi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}

enum class Bite {
    STRAIGHT,
    UNDERBITE,
    OVERBITE
}

enum class Behavour {
    ACTIVE,
    PASSIVE
}

interface Animal {
    val weight : Double
    val age : Int
}

interface Dog : Animal {
    val biteType : Bite
}

interface Cat : Animal {
    val behavourType : Behavour
}

class Husky(override val weight: Double, override val age: Int, override val biteType: Bite) : Dog

class Corgy(override val weight: Double, override val age: Int, override val biteType: Bite) : Dog

class Scottish(override val weight: Double, override val age: Int, override val behavourType : Behavour) : Cat

class Siamese(override val weight: Double, override val age: Int, override val behavourType : Behavour) : Cat

class ZooMarket {
    fun defineAnimal(animal: Animal): String {
        return when(animal) {
            is Dog -> "это пёсель"
            is Cat -> "это котяра"
            else -> "ты кого привёл?"
        }
    }
    fun defineForBreed(breed : String) : String {
        return when (breed) {
            "Husky" -> "Dog"
            "Corgy" -> "Dog"
            "Scottish" -> "Cat"
            "Siamese" -> "Cat"
            else -> "Неизвестная порода"
        }
    }
}

fun main() {
    val husky = Husky(25.0, 3, Bite.STRAIGHT)
    val corgy = Corgy(12.0, 2, Bite.UNDERBITE)
    val scottishFold = Scottish(6.0, 4, Behavour.PASSIVE)
    val siameseCat = Siamese(5.0, 5, Behavour.ACTIVE)

    val zooShop = ZooMarket()

    println(zooShop.defineAnimal(husky))
    println(zooShop.defineAnimal(corgy))
    println(zooShop.defineAnimal(scottishFold))
    println(zooShop.defineAnimal(siameseCat))

    println(zooShop.defineForBreed("Husky"))
    println(zooShop.defineForBreed("Corgy"))
    println(zooShop.defineForBreed("Scottish"))
    println(zooShop.defineForBreed("Siamese"))
}