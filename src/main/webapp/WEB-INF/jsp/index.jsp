<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Job Portal - Find Your Next Role</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="/">Job Portal</a>
        <a class="btn btn-outline-light" href="/post-job">+ Post a Job</a>
    </div>
</nav>

<div class="container">

    <form method="get" action="/" class="d-flex mb-4">
        <input type="text" name="keyword" class="form-control me-2"
               placeholder="Search by job title..." value="${keyword}">
        <button type="submit" class="btn btn-primary">Search</button>
    </form>

    <h4 class="mb-3">
        <c:choose>
            <c:when test="${not empty keyword}">Results for "${keyword}"</c:when>
            <c:otherwise>All Open Positions</c:otherwise>
        </c:choose>
    </h4>

    <c:choose>
        <c:when test="${empty jobs}">
            <div class="alert alert-info">No job postings found.</div>
        </c:when>
        <c:otherwise>
            <div class="row">
                <c:forEach var="job" items="${jobs}">
                    <div class="col-md-6 mb-3">
                        <div class="card shadow-sm h-100">
                            <div class="card-body">
                                <h5 class="card-title">${job.title}</h5>
                                <h6 class="card-subtitle mb-2 text-muted">${job.employer.companyName}</h6>
                                <p class="card-text">
                                    <strong>Location:</strong> ${job.location}<br>
                                    <c:if test="${not empty job.salary}">
                                        <strong>Salary:</strong> &#8377;${job.salary}<br>
                                    </c:if>
                                    <strong>Posted:</strong> ${job.postedDate}
                                </p>
                                <a href="/jobs/${job.id}" class="btn btn-sm btn-primary">View Details</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>
