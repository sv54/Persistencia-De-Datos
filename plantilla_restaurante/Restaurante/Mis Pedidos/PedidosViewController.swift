
import UIKit
import CoreData

class PedidosViewController: UIViewController {

    var frc : NSFetchedResultsController<Pedido>!
    var pedidos: [Pedido] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        
        let miDelegate = UIApplication.shared.delegate! as! AppDelegate
        let miContexto = miDelegate.persistentContainer.viewContext

        let consulta = NSFetchRequest<Pedido>(entityName: "Pedido")
        let sortDescriptors = [NSSortDescriptor(key:"fecha", ascending:false)]
        consulta.sortDescriptors = sortDescriptors
        self.frc = NSFetchedResultsController<Pedido>(fetchRequest: consulta, managedObjectContext: miContexto, sectionNameKeyPath: nil, cacheName: nil)

        
        try! self.frc.performFetch()
        if let resultados = frc.fetchedObjects {
            print("Hay \(resultados.count) pedidos")
            for pedido in resultados {
                print ("Pedido realizado con fecha: ", pedido.fecha!)
            }
        }
        
        
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
