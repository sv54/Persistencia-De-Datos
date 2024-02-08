//
//  TiempoViewModel.swift
//  ElTiempo_MVVM
//
//  Created by Otto Colomina Pardo on 20/1/22.
//

import Foundation
import Combine
import UIKit

class TiempoViewModel {
    let modelo = TiempoModelo()
    @Published var estado = ""
    @Published var icono: UIImage?
    
    func cosultarTiempoActual(localidad: String){
        modelo.consultarTiempoActual(localidad: localidad){
            estado, urlIcono in OperationQueue.main.addOperation{
                self.estado = estado

                /*if let iconoURL = URL(string: urlIcono), !urlIcono.isEmpty {
                    URLSession.shared.dataTaskPublisher(for: iconoURL).tryMap{data, response -> Data in guard let httpResponse = response as? HTTPURLResponse, httpResponse.statusCode == 200 else{
                        throw URLError(.badServerResponse)
                    }
                        return UIImage(data:data) ?? UIImage()
                    }.receive(on: DispatchQueue.main).assign
                        
                    
                }*/
            }
        }
    }
}
