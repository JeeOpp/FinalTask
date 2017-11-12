package service;

/**
 * Created by DNAPC on 12.11.2017.
 */
public interface AuthorizationService {
    Boolean registration(String ... args);
    String authentication(String ... args);
}
