package Horarios;

import akka.actor.UntypedActor;

public class Paciente extends UntypedActor {
    
    public enum Mensaje {
        GRACIAS_POR_SU_PREFERENCIA,
        ESCUCHA_NOMBRE
    }    
    
    @Override
    public void onReceive(Object o) throws Exception {
        System.out.println("[Paciente]: " + o); 
        if (o == Mensaje.ESCUCHA_NOMBRE) {
            Principal.enfermera.tell(Enfermera.Mensaje.PACIENTE_PRESENTE, getSelf());
        } 
        else if (o == Mensaje.GRACIAS_POR_SU_PREFERENCIA) {
            getContext().stop(getSelf());
        } else {
            unhandled(o);
        }
    } 
}