//
//  ViewController.swift
//  PruebaArchivo
//
//  Created by user245981 on 1/9/24.
//

import UIKit

class ViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        let file = "prueba.txt"
        let text = "hola iOS"
        
        if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first{
            let fileURL = dir.appendingPathComponent(file)
            try! text.write(to: fileURL, atomically: false, encoding: .utf8)
            
        }
    }
}
