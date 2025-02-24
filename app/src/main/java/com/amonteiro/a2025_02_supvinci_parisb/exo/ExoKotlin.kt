package com.amonteiro.a2025_02_supvinci_parisb.exo

var v2: String? = "toto"

fun main() {

    var res = boulangerie(nbS = 2)
    println("res=${res}")

}

fun boulangerie(nbC: Int = 0, nbB: Int = 0, nbS: Int = 0): Double = nbC * PRICE_CROISSANT + nbB * PRICE_BAGUETTE + nbS * PRICE_SANDWITCH


