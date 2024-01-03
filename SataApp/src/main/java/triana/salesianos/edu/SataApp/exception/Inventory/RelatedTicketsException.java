package triana.salesianos.edu.SataApp.exception.Inventory;

public class RelatedTicketsException extends RuntimeException{
    public RelatedTicketsException(){super("The item can't be deleted with tickets associated");}

}
