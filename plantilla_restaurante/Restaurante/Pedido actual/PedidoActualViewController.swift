

import UIKit
import CoreData

class PedidoActualViewController: UIViewController, UITableViewDataSource, LineaPedidoTableViewCellDelegate {

    @IBOutlet weak var tabla: UITableView!
    @IBOutlet weak var totalLabel: UILabel!
    
    var lineasPedido: [LineaPedido] = []
    
    @IBAction func realizarPedidoPulsado(_ sender: Any) {
        let miDelegate = UIApplication.shared.delegate as! AppDelegate
        let miContexto = miDelegate.persistentContainer.viewContext
        
        if let pedidoActual = StateSingleton.shared.pedidoActual {
            pedidoActual.fecha = Date()
            

            do {
                try miContexto.save()

                StateSingleton.shared.pedidoActual = Pedido(context: miContexto)

                print("¡Pedido realizado! Su pedido está en camino.")
            } catch {

                print("Error al realizar el pedido en Core Data: \(error)")
                
            }
            mostrarAlerta("Pedido Realizado!", mensaje: "Su pedido está en camino")
            tabla.reloadData()
        }
    }
    
    
    @IBAction func cancelarPedidoPulsado(_ sender: Any) {
        
        let miDelegate = UIApplication.shared.delegate as! AppDelegate
        let miContexto = miDelegate.persistentContainer.viewContext
        
        if let pedidoActual = StateSingleton.shared.pedidoActual {

            miContexto.delete(pedidoActual)


            do {
                try miContexto.save()

                StateSingleton.shared.pedidoActual = Pedido(context: miContexto)

                print("Pedido cancelado.")
            } catch {

                print("Error al cancelar el pedido en Core Data: \(error)")
            }
            
            tabla.reloadData()
            mostrarAlerta("Pedido cancelado", mensaje: "Se ha cancelado vuestro pedido")
        }
    }
    
    func mostrarAlerta(_ titulo: String, mensaje: String) {
        let alertController = UIAlertController(title: titulo, message: mensaje, preferredStyle: .alert)
        let okAction = UIAlertAction(title: "OK", style: .default, handler: nil)
        alertController.addAction(okAction)
        present(alertController, animated: true, completion: nil)
    }
    
    //TODO: descomentar esta línea!!!!
    var platoElegido : Plato!
    

    override func viewDidLoad() {
        super.viewDidLoad()
        self.tabla.dataSource = self
        
        //print(platoElegido?.descripcion ?? "sin descripcion")
        let miDelegate = UIApplication.shared.delegate as! AppDelegate
        let miContexto = miDelegate.persistentContainer.viewContext
        //TODO:
        // - crear el pedido actual si no existe
        // - crear la linea de pedido y asociarla al plato y al pedido actual
        if(StateSingleton.shared.pedidoActual==nil){
            let nuevoPedido = Pedido(context: miContexto)
            nuevoPedido.fecha = Date()
            StateSingleton.shared.pedidoActual = nuevoPedido
        }
        
        do {
            try miContexto.save()
        } catch {
            print("Error al guardar el nuevo pedido en Core Data: \(error)")
        }
        
        if let pedidoActual = StateSingleton.shared.pedidoActual, let platoElegido = platoElegido {

            let nuevaLineaPedido = LineaPedido(context: miContexto)
            nuevaLineaPedido.cantidad = 1
            nuevaLineaPedido.plato = platoElegido
            nuevaLineaPedido.pedido = pedidoActual
            platoElegido.addToLineasPedido(nuevaLineaPedido)
            

        do {
            try miContexto.save()
            } catch {
                print("Error al guardar la nueva línea de pedido en Core Data: \(error)")
            }
        }
        

    }
   
    
    override func viewWillAppear(_ animated: Bool) {
        self.tabla.reloadData()
    }
    

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if let lineasPedido = StateSingleton.shared.pedidoActual?.lineasPedido{
            return lineasPedido.count
        }
        return 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let celda = tableView.dequeueReusableCell(withIdentifier: "celdaLinea", for: indexPath) as! LineaPedidoTableViewCell
        celda.pos = indexPath.row
        celda.delegate = self
        
        
        if let lineasPedido = StateSingleton.shared.pedidoActual?.lineasPedido {
            if let lineaPedido = lineasPedido.object(at: indexPath.row) as? LineaPedido{
                celda.nombreLabel.text = lineaPedido.plato?.nombre ?? "sin nombre we"
                celda.cantidadLabel.text = "\(lineaPedido.cantidad)"
            }
        }
        
        return celda
    }
    
    func cantidadCambiada(posLinea: Int, cantidad: Int) {
        //TODO: actualizar la cantidad de la línea de pedido correspondiente
        let miDelegate = UIApplication.shared.delegate as! AppDelegate
        let miContexto = miDelegate.persistentContainer.viewContext
        
        if let pedidoActual = StateSingleton.shared.pedidoActual,
           let lineasPedido = pedidoActual.lineasPedido,
           lineasPedido.count > posLinea,
           let lineaPedido = lineasPedido.object(at: posLinea) as? LineaPedido{

            let cantidad = max(1, min(cantidad,100))
            lineaPedido.cantidad = Int16(cantidad)
            
        }
        do {
            try miContexto.save()
        } catch {
            print("Error al guardar la nueva línea de pedido en Core Data: \(error)")
        }
        
    }
    
}
