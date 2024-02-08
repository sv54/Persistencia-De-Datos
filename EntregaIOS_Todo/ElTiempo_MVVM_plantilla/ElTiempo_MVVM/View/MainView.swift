//
//  ViewController.swift
//  ElTiempo_MVVM
//
//

import UIKit
import Combine

class MainView: UIViewController {

    @IBOutlet weak var estadoLabel: UILabel!
    @IBOutlet weak var estadoImage: UIImageView!
    @IBOutlet weak var campoTexto: UITextField!
    
    let viewModel = TiempoViewModel()
    private var cancellables: Set<AnyCancellable> = []
    
    @IBAction func botonPulsado(_ sender: Any) {
        viewModel.cosultarTiempoActual(localidad: campoTexto.text!)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        estadoLabel.text = viewModel.estado
        viewModel.$estado.assign(to: \.text!, on: estadoLabel).store(in: &cancellables)
        // Do any additional setup after loading the view.
    }


}

