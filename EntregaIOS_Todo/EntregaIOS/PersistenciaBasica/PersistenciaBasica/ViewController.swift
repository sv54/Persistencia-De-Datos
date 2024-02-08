//
//  ViewController.swift
//  PersistenciaBasica
//
//  Created by user245981 on 1/9/24.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var textView: UITextView!
    
    @IBOutlet weak var label: UILabel!
    
    var fechaEdicion: Date = Date()
    var preferences = UserDefaults()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.textView.text = self.preferences.string(forKey: "texto")
        self.fechaEdicion = self.preferences.object(forKey: "fecha") as? Date ?? Date(timeIntervalSince1970: 0)
        self.label.text = DateFormatter.localizedString(from: self.fechaEdicion, dateStyle: .short, timeStyle: .medium)
        
        // Do any additional setup after loading the view.
    }

    @IBAction func guardar(_ sender: Any) {
        self.fechaEdicion = Date()
        self.label.text = DateFormatter.localizedString(from: self.fechaEdicion, dateStyle: .short, timeStyle: .medium)

        
        self.preferences.set(self.textView.text, forKey: "texto")
        self.preferences.set(self.fechaEdicion, forKey: "fecha")
    }
    
}

