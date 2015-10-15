<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="row-fluid">
	<div class="jumbotron">
		<h1>
			<spring:message code='project.name' />
			!
		</h1>
	</div>
</div>
<div class="row-fluid">
	<div class="span4 offset4 well" ng-controller="loginController">
		<legend>
			<spring:message code="login.header" />
		</legend>
		<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
			<div class="alert alert-error" >  <!-- ng-class="{'': displayLoginError == true, 'none': displayLoginError == false}" -->
				<spring:message code="login.error" />
				Your login attempt was not successful due <c:out
					value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
			</div>
		</c:if>

		<form method="post" action="j_spring_security_check">
<%-- 			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
			<div>
				<input name="username" id="username" type="text" class="span12"
					placeholder="<spring:message code='sample.email' /> "><br />
				<input name="password" id="password" type="password" class="span12"
					placeholder="Password"><br />
				<button type="submit" name="submit"
					class="btn btn-inverse btn-block">
					<spring:message code="login.signIn" />
				</button>
			</div>

			<div class="span4 offset4 well" ng-controller="loginController">
				<button type="button" ng-click="changeErrorVariableValue()">Change</button>
			</div>
		</form>
	</div>
</div>
<script src="<c:url value='/resources/js/pages/login.js' />"></script>