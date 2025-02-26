package com.amonteiro.a2025_02_supvinci_parisb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amonteiro.a2025_02_supvinci_parisb.model.PictureBean
import com.amonteiro.a2025_02_supvinci_parisb.model.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

fun main() {
    val viewModel = MainViewModel()
    viewModel.loadWeathers("Nice")

    //Affichage de la liste (qui doit être remplie) contenue dans la donnée observable
    println("List : ${viewModel.dataList.value}")
}

class MainViewModel : ViewModel() {
    //MutableStateFlow est une donnée observable
    val dataList = MutableStateFlow(emptyList<PictureBean>())

    fun loadWeathers(cityName: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val weatherlist = WeatherRepository.loadWeathers(cityName)
            dataList.value = weatherlist.map { city->
                PictureBean(
                    id = city.id.toInt(),
                    url = city.weather.firstOrNull()?.icon ?: "",
                    title = city.name,
                    longText = """
           Il fait ${city.main.temp}° à ${city.name} (id=${city.id}) avec un vent de ${city.wind.speed} m/s
           -Description : ${city.weather.getOrNull(0)?.description ?: "-"}
           -Icône : ${city.weather.getOrNull(0)?.icon ?: "-"}
       """.trimIndent()
                )
            }
        }
    }
}