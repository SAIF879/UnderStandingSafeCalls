package com.example.understandingsafecalls.util

fun main(){
    val list = listOf(1,2,3,4,5)
    list.fold(0){acc, i->
        acc+i
    }
    //fold is an acuumulator
}