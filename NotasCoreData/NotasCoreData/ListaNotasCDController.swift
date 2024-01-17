//
//  ListaNotasCDController.swift
//  NotasCoreData
//
//  Created by Máster Móviles on 17/1/24.
//

import UIKit
import CoreData

class ListaNotasCDController: UITableViewController, NSFetchedResultsControllerDelegate {

    var frc : NSFetchedResultsController<Nota>!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        let miDelegate = UIApplication.shared.delegate! as! AppDelegate
        let miContexto = miDelegate.persistentContainer.viewContext

        let consulta = NSFetchRequest<Nota>(entityName: "Nota")
        let sortDescriptors = [NSSortDescriptor(key:"contenido", ascending:true)]
        consulta.sortDescriptors = sortDescriptors
        self.frc = NSFetchedResultsController<Nota>(fetchRequest: consulta, managedObjectContext: miContexto, sectionNameKeyPath: "inicial", cacheName: "miCache")

        //ejecutamos el fetch
        try! self.frc.performFetch()
        
        if let resultados = frc.fetchedObjects {
            print("Hay \(resultados.count) mensajes")
            for mensaje in resultados {
                print (mensaje.contenido!)
            }
        }
        
        self.frc.delegate = self;
        
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }

    // MARK: - Table view data source
    
    /* obtener el título de una sección dada */
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
            return self.frc.sections?[section].name
    }

    
    
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            let miDelegate = UIApplication.shared.delegate as! AppDelegate
            let miContexto = miDelegate.persistentContainer.viewContext
            //FALTA: eliminar del contexto el objeto en la posición indexPath
            //Tenéis que obtenerlo del fetched results controller
            //parecido a como se hace para pintar la celda
            let objetoAEliminar = frc.object(at: indexPath)
            
            // Eliminar el objeto del contexto
            miContexto.delete(objetoAEliminar)
            
            // Guardar el contexto
            do {
                try miContexto.save()
            } catch {
                // Manejar el error en caso de que la operación de guardado falle
                print("Error al guardar el contexto: \(error)")
            }
        }
    }


    override func numberOfSections(in tableView: UITableView) -> Int {
        return self.frc.sections!.count
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.frc.sections![section].numberOfObjects
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        //recordar que el prototipo de celda tiene un "reuse identifier"
        //que hay que asignar en el storyboard
        let cell = tableView.dequeueReusableCell(withIdentifier: "miCelda", for: indexPath)

        let mensaje = self.frc.object(at: indexPath)
        cell.textLabel?.text = mensaje.contenido!
        return cell
    }
    
    func controllerWillChangeContent(_ controller: NSFetchedResultsController<NSFetchRequestResult>) {
        self.tableView.beginUpdates()
    }

    func controllerDidChangeContent(_ controller: NSFetchedResultsController<NSFetchRequestResult>) {
        self.tableView.endUpdates()
    }

    func controller(_ controller: NSFetchedResultsController<NSFetchRequestResult>, didChange anObject: Any, at indexPath: IndexPath?, for type: NSFetchedResultsChangeType, newIndexPath: IndexPath?) {
        switch type {
        case .insert:
            self.tableView.insertRows(at: [newIndexPath!], with:.automatic )
        case .update:
            self.tableView.reloadRows(at: [indexPath!], with: .automatic)
        case .delete:
            self.tableView.deleteRows(at: [indexPath!], with: .automatic)
        case .move:
            self.tableView.deleteRows(at: [indexPath!], with: .automatic)
            self.tableView.insertRows(at: [newIndexPath!], with:.automatic )
        }
    }

    func controller(_ controller: NSFetchedResultsController<NSFetchRequestResult>, didChange sectionInfo: NSFetchedResultsSectionInfo, atSectionIndex sectionIndex: Int, for type: NSFetchedResultsChangeType) {
        switch(type) {
        case .insert:
            self.tableView.insertSections(IndexSet(integer:sectionIndex), with: .automatic)
        case .delete:
            self.tableView.deleteSections(IndexSet(integer:sectionIndex), with: .automatic)
        default: break
        }
    }


    /*
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "reuseIdentifier", for: indexPath)

        // Configure the cell...

        return cell
    }
    */

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
