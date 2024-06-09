package pt.ipp.isep.dei.esoft.project.application.session;

import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;

/**
 * The type User session.
 */
public class UserSession {

    private pt.isep.lei.esoft.auth.UserSession userSession;

    /**
     * Instantiates a new User session.
     *
     * @param userSession the user session
     */
    public UserSession(pt.isep.lei.esoft.auth.UserSession userSession) {
        this.userSession = userSession;
    }

    /**
     * Gets user email.
     *
     * @return the user email
     */
    public String getUserEmail() {
        return userSession.getUserId().getEmail();
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return this.userSession.getUserName();
    }

    /**
     * Gets user roles.
     *
     * @return the user roles
     */
    public List<UserRoleDTO> getUserRoles() {
        return this.userSession.getUserRoles();
    }

    /**
     * Do logout.
     */
    public void doLogout() {
        this.userSession.doLogout();
    }

    /**
     * Is logged in boolean.
     *
     * @return the boolean
     */
    public boolean isLoggedIn() {
        return this.userSession.isLoggedIn();
    }

    /**
     * Is logged in with role boolean.
     *
     * @param roleId the role id
     * @return the boolean
     */
    public boolean isLoggedInWithRole(String roleId) {
        return this.userSession.isLoggedInWithRole(roleId);
    }
}