package com.navarna.computerdb.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class RestException {
    
   public static RestExceptionNotFound notFound() {
       return new RestExceptionNotFound();
   }
   
   public static RestExceptionIncorrectArgument illegalArgument() {
       return new RestExceptionIncorrectArgument();
   }

   public static RestExceptionInexistent inexistent() {
       return new RestExceptionInexistent();
   }
}


@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "Object not in database")
class RestExceptionNotFound extends RuntimeException{
    private static final long serialVersionUID = 1L;
}
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "incorrectArgument")
class RestExceptionIncorrectArgument extends RuntimeException{
    private static final long serialVersionUID = 1L;
}

@ResponseStatus(value= HttpStatus.CONFLICT, reason = "element inexistent")
class RestExceptionInexistent extends RuntimeException{
    private static final long serialVersionUID = 1L;
}