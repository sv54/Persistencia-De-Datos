//
//  ViewController.swift
//  NotasCoreData
//
//  Created by Máster Móviles on 12/1/24.
//

import UIKit
import CoreData

class ViewController: UIViewController {

    @IBOutlet weak var fechaLabel: UILabel!
    @IBOutlet weak var textView: UITextView!
    @IBOutlet weak var mensajeLabel: UILabel!
    
    
    @IBOutlet weak var pickerViewLibreta: UIPickerView!
    let miGestorPicker = GestorPicker()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        //Aquí, "picker" es el outlet que representa al "picker view"
        //CAMBIALO por el nombre que le hayas dado
        self.pickerViewLibreta.delegate = self.miGestorPicker
        self.pickerViewLibreta.dataSource = self.miGestorPicker
        //cargamos las libretas con Core Data
        self.miGestorPicker.cargarLista()
        


    }
    


    @IBAction func Guardar(_ sender: Any) {
        guard let miDelegate = UIApplication.shared.delegate as? AppDelegate else {
            return
        }
        
        let miContexto = miDelegate.persistentContainer.viewContext
        
        let nuevaNota = Nota(context:miContexto)
        nuevaNota.fecha = Date()
        nuevaNota.texto = textView.text
        let numLibreta = self.pickerViewLibreta.selectedRow(inComponent: 0)
        nuevaNota.libreta = self.miGestorPicker.libretas[numLibreta]
        do {
           try miContexto.save()
        } catch {
           print("Error al guardar el contexto: \(error)")
        }
        fechaLabel.text = DateFormatter.localizedString(
            from:nuevaNota.fecha!,
                     dateStyle: .short, timeStyle: .medium)
        mensajeLabel.text = "Nota guardada"

        
    }
    
    @IBAction func Crear(_ sender: Any) {
        fechaLabel.text = ""
        textView.text = ""
        mensajeLabel.text = ""
    }
    @IBAction func nuevaLibreta(_ sender: Any) {
        guard let miDelegate = UIApplication.shared.delegate as? AppDelegate else {
            return
        }
        
        let miContexto = miDelegate.persistentContainer.viewContext
        

        
        let alert = UIAlertController(title: "Nueva libreta",
                                      message: "Escribe el nombre para la nueva libreta",
                                      preferredStyle: .alert)
        let crear = UIAlertAction(title: "Crear", style: .default) {
            action in
            let nombre = alert.textFields![0].text!
            //AQUI FALTA GUARDAR LA LIBRETA CON CORE DATA
            let nuevaLibreta = Libreta(context:miContexto)
            nuevaLibreta.nombre = nombre

            do {
                try miContexto.save()
                self.miGestorPicker.libretas.append(nuevaLibreta)
                self.pickerViewLibreta.reloadAllComponents()

            } catch {
               print("Error al guardar el contexto: \(error)")
            }
        }
        let cancelar = UIAlertAction(title: "Cancelar", style: .cancel) {
            action in
        }
        alert.addAction(crear)
        alert.addAction(cancelar)
        alert.addTextField() { $0.placeholder = "Nombre"}
        self.present(alert, animated: true)
    }
}

