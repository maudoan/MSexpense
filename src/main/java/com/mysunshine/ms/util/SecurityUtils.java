package com.mysunshine.ms.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user
     */
    public static String getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String) {
            return (String) authentication.getCredentials();
        }
        return null;
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the isUserInRole() method in the Servlet API
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
        }
        return false;
    }

    public static boolean isCurrentUserInRoles(Set<String> authorities) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Boolean result = false;
        for(String authority: authorities){
            if (authentication != null) {
                result = authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
                if(!result) return result;
            }
        }
        return result;
    }

    public static boolean hasRoleWithTenant(Long tenantId){
        return true;
    }


    public static boolean hasAnyAuthority(String[] authorities) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Boolean hasAuthority = false;
        if(authentication == null) return hasAuthority;

        for (String auth : authorities){
            if(authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(auth))){
                hasAuthority = true;
                break;
            }
        }

        return hasAuthority;
    }

    /**
     *
     * @param input
     * @return
     */
    public static String getMd5Hash(String input)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32)
            {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param inputStr
     * @param key
     * @return
     */
    public static String encryptCaesarData(String inputStr, int key) {
        inputStr = inputStr.replaceAll("[^0-9]", "");

        StringBuilder encryptStr = new StringBuilder();

        for (int i = 0; i < inputStr.length(); i++) {
            int digit = Character.getNumericValue(inputStr.charAt(i));
            int encryptDigit = (digit + key) % 10;
            encryptStr.append(encryptDigit);
        }

        return encryptStr.toString();
    }

    /**
     *
     * @param inputStr
     * @param shiftKey
     * @return
     */
    public static String decryptCaesarData(String inputStr, int shiftKey) {
        StringBuilder decryptStr = new StringBuilder();

        for (int i = 0; i < inputStr.length(); i++) {
            int digit = Character.getNumericValue(inputStr.charAt(i));
            int decryptDigit = (digit - shiftKey) % 10;
            if (decryptDigit < 0){
                decryptDigit += 10;
            }
            decryptStr.append(decryptDigit);
        }

        // return decrypted string
        return decryptStr.toString();
    }
}
