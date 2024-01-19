

import UIKit
import CoreData

class PlatosViewController: UIViewController, UITableViewDataSource, PlatoTableViewCellDelegate, UISearchResultsUpdating  {

    
    
    
    @IBOutlet weak var tabla: UITableView!
    
    var frc : NSFetchedResultsController<Plato>!
    var searchController : UISearchController!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.tabla.dataSource = self
        //TODO: crear un NSFetchedResultsController
        
        
        let miDelegate = UIApplication.shared.delegate! as! AppDelegate
        let miContexto = miDelegate.persistentContainer.viewContext

        let consulta = NSFetchRequest<Plato>(entityName: "Plato")
        let sortDescriptors = [NSSortDescriptor(key:"tipo", ascending:false)]
        consulta.sortDescriptors = sortDescriptors
        self.frc = NSFetchedResultsController<Plato>(fetchRequest: consulta, managedObjectContext: miContexto, sectionNameKeyPath: "tipo", cacheName: nil)

        //ejecutamos el fetch
        try! self.frc.performFetch()
        
        self.searchController = UISearchController(searchResultsController: nil)
        
        self.searchController.searchResultsUpdater = self
        //Configuramos el search controller
        self.searchController.obscuresBackgroundDuringPresentation = false
        self.searchController.searchBar.placeholder = "Buscar texto"
        //Lo añadimos a la tabla
        self.searchController.searchBar.sizeToFit()
        self.tabla.tableHeaderView = searchController.searchBar


    }
    
    func updateSearchResults(for searchController: UISearchController) {
        
        let textoBusqueda = searchController.searchBar.text!
        let miDelegate = UIApplication.shared.delegate as! AppDelegate
        let miContexto = miDelegate.persistentContainer.viewContext
        let request = NSFetchRequest<Plato>(entityName: "Plato")

        if !textoBusqueda.isEmpty {
            let pred = NSPredicate(format: "nombre CONTAINS[cd] %@", textoBusqueda)
            request.predicate = pred
        } else {
            request.predicate = nil // Sin predicado cuando el texto de búsqueda está vacío
        }
        
        let sortDescriptors = [NSSortDescriptor(key:"tipo", ascending:false)]
        request.sortDescriptors = sortDescriptors
        self.frc = NSFetchedResultsController<Plato>(fetchRequest: request, managedObjectContext: miContexto, sectionNameKeyPath: "tipo", cacheName: nil)
        
        try! self.frc.performFetch()
        self.tabla.reloadData()


    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.frc.sections![section].numberOfObjects
    }
    
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return self.frc.sections?[section].name
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return frc.sections!.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let celda = tableView.dequeueReusableCell(withIdentifier: "celdaPlato", for: indexPath) as! PlatoTableViewCell
        
        //Necesario para que funcione el botón "añadir"
        celda.delegate = self
        celda.index = indexPath
        
        //TODO: rellenar la celda con los datos del plato: nombre, precio y descripción
        //Para formato moneda puedes usar un NumberFormatter con estilo moneda

        
        let plato = self.frc.object(at: indexPath)
        celda.nombreLabel.text = plato.nombre
        celda.descripcionLabel.text = plato.descripcion
        
        let fmt = NumberFormatter()
        fmt.numberStyle = .currency
        let formateado = fmt.string(from: NSNumber(value: plato.precio)) //€10.70
        
        celda.precioLabel.text = formateado
        
        return celda
    }
    
    //Se ha pulsado el botón "Añadir"
    func platoAñadido(indexPath: IndexPath) {
        //TODO: obtener el Plato en la posición elegida
        let platoElegido : Plato! = frc.object(at: indexPath)
        
        
        //Le pasamos el plato elegido al controller de la pantalla de pedido
        //Y saltamos a esa pantalla
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let vc = storyboard.instantiateViewController(withIdentifier: "Tu Pedido") as! PedidoActualViewController
        
        //TODO: DESCOMENTAR ESTA LINEA!!!!!!!!!
        vc.platoElegido = platoElegido
        
        
        navigationController?.pushViewController(vc, animated: true)
        
    }
    

}
