//
//  ListaNotasController.swift
//  NotasCoreData
//
//  Created by Máster Móviles on 12/1/24.
//

import UIKit
import CoreData

class ListaNotasController: UITableViewController, UISearchResultsUpdating {
    var listaNotas : [Nota]!
    let throttler = Throttler(minimumDelay: 0.5)

    //esto debe ser una variable miembro de ListaNotasController
    let searchController = UISearchController(searchResultsController: nil)

    
    override func viewDidLoad() {
        super.viewDidLoad()

        
        //iOS intentará pintar la tabla, hay que inicializarla aunque sea vacía
        self.listaNotas = []
        //ListaNotasController recibirá lo que se está escribiendo en la barra de búsqueda
        searchController.searchResultsUpdater = self
        //Configuramos el search controller
        searchController.obscuresBackgroundDuringPresentation = false
        searchController.searchBar.placeholder = "Buscar texto"
        //Lo añadimos a la tabla
        searchController.searchBar.sizeToFit()
        self.tableView.tableHeaderView = searchController.searchBar

        /*
        let queryLibretas = NSFetchRequest<Libreta>(entityName:"Libreta")
        guard let miDelegate = UIApplication.shared.delegate as? AppDelegate else {
            return
        }
        let miContexto = miDelegate.persistentContainer.viewContext
        let libretas = try! miContexto.fetch(queryLibretas)
        for libreta in libretas {
            print(libreta.nombre)
        }*/
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }
    
    override func viewWillAppear(_ animated: Bool) {
        
        guard let miDelegate = UIApplication.shared.delegate as? AppDelegate else {
            return
        }
        
        let miContexto = miDelegate.persistentContainer.viewContext
        
        let request = Nota.fetchRequest()

        let notas = try! miContexto.fetch(request)
        listaNotas = try! miContexto.fetch(request)
        for nota in notas {
           print(nota.contenido!)
        }
        self.tableView.reloadData()
    }

    func updateSearchResults(for searchController: UISearchController) {
        throttler.throttle {
            let textoBusqueda = searchController.searchBar.text!
            print("Buscando \(textoBusqueda)")
            let miDelegate = UIApplication.shared.delegate as! AppDelegate
            let miContexto = miDelegate.persistentContainer.viewContext
            let request = Nota.fetchRequest()
            let pred = NSPredicate(format: "contenido CONTAINS[c] %@",textoBusqueda)
            request.predicate = pred
            let resultados = try! miContexto.fetch(request)
            
            self.listaNotas = resultados
            self.tableView.reloadData()
        }

    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return listaNotas.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MiCelda", for: indexPath)
        cell.textLabel?.text = self.listaNotas[indexPath.row].contenido as? String
        cell.detailTextLabel?.text = self.listaNotas[indexPath.row].libreta?.nombre as? String
        // Configure the cell...

        return cell
    }
    

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
