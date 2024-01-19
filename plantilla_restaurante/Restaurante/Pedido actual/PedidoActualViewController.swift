

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
            // Asigna la fecha actual al pedido
            pedidoActual.fecha = Date()
            
            // Guarda el contexto para persistir los cambios
            do {
                try miContexto.save()
                // Crea un nuevo pedido en StateSingleton.shared.pedidoActual
                StateSingleton.shared.pedidoActual = Pedido(context: miContexto)
                // Muestra un mensaje al usuario
                print("¡Pedido realizado! Su pedido está en camino.")
            } catch {
                // Maneja los errores de guardado aquí
                print("Error al realizar el pedido en Core Data: \(error)")
            }
        }
    }
    
    
    @IBAction func cancelarPedidoPulsado(_ sender: Any) {
        
        let miDelegate = UIApplication.shared.delegate as! AppDelegate
        let miContexto = miDelegate.persistentContainer.viewContext
        
        if let pedidoActual = StateSingleton.shared.pedidoActual {
            // Elimina el pedido actual y sus líneas de pedido
            miContexto.delete(pedidoActual)

            // Guarda el contexto para persistir los cambios
            do {
                try miContexto.save()
                // Crea un nuevo pedido en StateSingleton.shared.pedidoActual
                StateSingleton.shared.pedidoActual = Pedido(context: miContexto)
                // Muestra un mensaje al usuario
                print("Pedido cancelado.")
            } catch {
                // Maneja los errores de guardado aquí
                print("Error al cancelar el pedido en Core Data: \(error)")
            }
        }
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
            // Maneja los errores de guardado aquí
            print("Error al guardar el nuevo pedido en Core Data: \(error)")
        }
        
        if let pedidoActual = StateSingleton.shared.pedidoActual, let platoElegido = platoElegido {
            // Crea una nueva línea de pedido
            let nuevaLineaPedido = LineaPedido(context: miContexto)
            nuevaLineaPedido.cantidad = 1
            nuevaLineaPedido.plato = platoElegido
            nuevaLineaPedido.pedido = pedidoActual
            platoElegido.addToLineasPedido(nuevaLineaPedido)
            // Guarda el contexto para persistir la nueva línea de pedido
            

        do {
            try miContexto.save()
            } catch {
                // Maneja los errores de guardado aquí
                print("Error al guardar la nueva línea de pedido en Core Data: \(error)")
            }
        }
        

    }
   
    
    override func viewWillAppear(_ animated: Bool) {
        self.tabla.reloadData()
        //print(platoElegido.lineasPedido?.count, ": num lineas pedido")
    }
    

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if let lineasPedido = StateSingleton.shared.pedidoActual?.lineasPedido{
            return lineasPedido.count
        }
        return 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let celda = tableView.dequeueReusableCell(withIdentifier: "celdaLinea", for: indexPath) as! LineaPedidoTableViewCell
        //Necesario para que funcione el delegate
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
            // Maneja los errores de guardado aquí
            print("Error al guardar la nueva línea de pedido en Core Data: \(error)")
        }
        
    }
    
}
