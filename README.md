# Traffic-API

## Endpoints

### /{camera_id} 
Retrieve a specific traffic camera by its primary key. 

```json
{
  "id": "camera-001",
  "status": "ACTIVE"
}
```

### /cameras
Paginates through all the cameras stored in the database.

```json
{
  "content": [
    {
      "id": "camera-001",
      "status": "ACTIVE"
    },
    {
      "id": "camera-00a",
      "status": "NOT_ACTIVE"
    }
  ],
  "pageable": {
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 20,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 2,
  "size": 20,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "first": true,
  "numberOfElements": 2,
  "empty": false
}
```

### /cameras/{status}

Paginates through all the cameras by their status.
`{status}` is either, active or not_active
```json
{
  "content": [
    {
      "id": "camera-00a",
      "status": "NOT_ACTIVE"
    }
  ],
  "pageable": {
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 20,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 1,
  "size": 20,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "first": true,
  "numberOfElements": 1,
  "empty": false
}
```

### /traffic-counts/{duration}
Paginates through all traffic counts for a given duration
