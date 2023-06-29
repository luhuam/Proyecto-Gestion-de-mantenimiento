package Horarios;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import java.util.ArrayList;

public class Enfermera extends UntypedActor{ 
    private static final long TIEMPO_DE_TRIAJE_PACIENTE = 2000;
    private ArrayList<ActorRef> pacientes;
    
    public enum Mensaje {
        PACIENTE_PRESENTE,
        LLAMAR_OTRO_PACIENTE
    } 
    
    private void pasarTriaje() throws InterruptedException {
        Thread.sleep(TIEMPO_DE_TRIAJE_PACIENTE);
    }
    
    @Override
    public void preStart() {
        pacientes = new ArrayList<>();
    }
    
    @Override
    public void onReceive(Object o) throws Exception {
        System.out.println("[Enfermera] ha recibido el mensaje: "+ o); 
        if (o == Mensaje.PACIENTE_PRESENTE) {
            System.out.println("[Enfermera] es notificado que el paciente está presente");
            pasarTriaje();
            System.out.println("[Enfermera] ha terminado de pesar, tallar y tomar la presión del paciente");
            pacientes.add(getSender());
            Principal.medico.tell(Medico.Mensaje.ATENDER_PACIENTE, getSelf());
        } 
        else if (o == Mensaje.LLAMAR_OTRO_PACIENTE) {            
            System.out.println("[Enfermera] es notificado para llamar a otro paciente");          
            if (!pacientes.isEmpty()) {
                pacientes.get(0).tell(Paciente.Mensaje.GRACIAS_POR_SU_PREFERENCIA, getSelf());
                pacientes.remove(0);
            }
        } else {
            unhandled(o);
        }
    }    
}