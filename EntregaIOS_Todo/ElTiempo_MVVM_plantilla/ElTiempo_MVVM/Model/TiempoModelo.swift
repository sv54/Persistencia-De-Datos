//
//  Tiempo.swift
//  ElTiempo_MVVM
//
//  Created by Otto Colomina Pardo on 20/1/22.
//
import Foundation

class TiempoModelo {
    let OW_URL_BASE = "https://api.openweathermap.org/data/2.5/weather?lang=es&units=metric&appid=1adb13e22f23c3de1ca37f3be90763a9&q="
    let OW_URL_BASE_ICON = "https://openweathermap.org/img/w/"
    
    func consultarTiempoActual(localidad:String, callback: @escaping (String, String)-> Void) {
            let urlString = OW_URL_BASE+localidad
            let url = URL(string:urlString)
            let dataTask = URLSession.shared.dataTask(with: url!) {
                datos, respuesta, error in
                do {
                    let tiempo = try JSONDecoder().decode(CurrentLocalWeather.self, from: datos!)
                    let estado = tiempo.weather[0].description
                    print(estado)
                    let icono = tiempo.weather[0].icon
                    let urlIcono = self.OW_URL_BASE_ICON+icono+".png"
                    callback(estado,urlIcono)
                }
                catch {
                    print(error)
                }
            }
            dataTask.resume()
    }
}
