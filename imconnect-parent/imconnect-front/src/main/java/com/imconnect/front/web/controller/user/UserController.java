package com.imconnect.front.web.controller.user;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.imconnect.core.model.user.User;
import com.imconnect.front.exception.PseudoInUseException;
import com.imconnect.front.service.user.UserService;
import com.imconnect.front.vo.UserListVO;
 
@Controller
@RequestMapping(value = "/protected/contacts", produces = "application/json;charset=UTF-8")
public class UserController {
 
    private static final String DEFAULT_PAGE_DISPLAYED_TO_USER = "0";
 
    @Autowired
    private UserService userService;
 
    @Autowired
    private MessageSource messageSource;
 
    @Value("10")
    private int maxResults;
 
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("usersList");
    }
    
//    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
//    public ResponseEntity<?> list(Locale locale) {
//        return createListAllResponse(0, locale);
//    }
 
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<?> list(@RequestParam int page, Locale locale) {
        return createListAllResponse(page, locale);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody User user,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) throws Exception{
    	
    	try {
    		userService.save(user);
    	} catch (PseudoInUseException e) {
    		throw new PseudoInUseException();
    	}
 
        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.create.success");
        }
 
        return createListAllResponse(page, locale, "message.create.success");
    }
 
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") int contactId,
                                    @RequestBody User contact,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) throws Exception{
        
    	if (contactId!=contact.getId().intValue()) {
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }
 
        try {
			userService.update(contact);
		} catch (PseudoInUseException e) {
			throw new PseudoInUseException();
		}
 
        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.update.success");
        }
 
        return createListAllResponse(page, locale, "message.update.success");
    }
 
    @RequestMapping(value = "/{contactId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("contactId") int contactId,
    								/*@RequestBody User user,*/
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
 
        try {
            userService.delete(Long.valueOf(contactId));
        } catch (AccessDeniedException e) {
            return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
        }
 
        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.delete.success");
        }
 
        return createListAllResponse(page, locale, "message.delete.success");
    }
 
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> search(@PathVariable("name") String name,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        return search(name, page, locale, null);
    }
 
    private ResponseEntity<?> search(String name, int page, Locale locale, String actionMessageKey) {
        UserListVO userListVO = userService.findByNameLike(page, maxResults, name);
 
        if (!StringUtils.isEmpty(actionMessageKey)) {
            addActionMessageToVO(userListVO, locale, actionMessageKey, null);
        }
 
        Object[] args = {name};
 
        addSearchMessageToVO(userListVO, locale, "message.search.for.active", args);
 
        return new ResponseEntity<UserListVO>(userListVO, HttpStatus.OK);
    }
 
    private UserListVO listAll(int page) {
        return userService.findAll(page, maxResults);
    }
 
    private ResponseEntity<UserListVO> returnListToUser(UserListVO contactList) {
        return new ResponseEntity<UserListVO>(contactList, HttpStatus.OK);
    }
 
    private ResponseEntity<?> createListAllResponse(int page, Locale locale) {
        return createListAllResponse(page, locale, null);
    }
 
    private ResponseEntity<?> createListAllResponse(int page, Locale locale, String messageKey) {
    	UserListVO userListVO = listAll(page);
 
        addActionMessageToVO(userListVO, locale, messageKey, null);
 
        return returnListToUser(userListVO);
    }
 
    private UserListVO addActionMessageToVO(UserListVO userListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return userListVO;
        }
 
        userListVO.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));
 
        return userListVO;
    }
 
    private UserListVO addSearchMessageToVO(UserListVO userListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return userListVO;
        }
 
        userListVO.setSearchMessage(messageSource.getMessage(actionMessageKey, args, null, locale));
 
        return userListVO;
    }
 
    private boolean isSearchActivated(String searchFor) {
        return !StringUtils.isEmpty(searchFor);
    }
    
    @ExceptionHandler(PseudoInUseException.class)
	public ResponseEntity<?> handleCustomException(PseudoInUseException ex, Locale locale) {
    	return new ResponseEntity<String>(messageSource.getMessage("users.error.pseudo.inuse", null, null, locale), HttpStatus.BAD_REQUEST);
    }
    
}