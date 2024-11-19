# Image Management API

- [Technical Task Documentation](https://docs.google.com/document/d/1Da7HKGfQrRWpLDCgy5T8DDdsh20wv5qOxHgwc94n4ig/edit?tab=t.0#heading=h.lgz645wnzwaz)
- [GitHub Repository](https://github.com/azarft/ImageAPI)



A RESTful API for managing images, built with **Spring Boot** and **PostgreSQL**. This API allows users to upload, manage, and delete images stored in Base64 format, and organize them into albums.

## Features

- **User Management**:
    - User registration and authentication.
    - Each user has access to their own albums and images.

- **Album Management**:
    - Create, retrieve, and delete albums.
    - Albums are linked to specific users.

- **Image Management**:
    - Upload images in Base64 format to a specific album.
    - Retrieve details about images, including metadata.
    - Delete images from the database.

- **Data Storage**:
    - Images are stored in PostgreSQL as Base64 strings.
    - Metadata (e.g., file name, size, upload date) is also stored in the database.

- **Validation and Security**:
    - File type validation for `.jpg`, `.jpeg`, and `.png`.
    - File size limited to 5 MB.
    - Basic authentication for securing user data.

## Technologies Used

- **Backend Framework**: Spring Boot
- **Database**: PostgreSQL
- **Data Format**: Base64 for storing images

## API Endpoints

### User Endpoints
| Method | Endpoint           | Description               |
|--------|--------------------|---------------------------|
| POST   | `/users/register`  | Register a new user       |
| POST   | `/users/login`     | Authenticate a user       |

### Album Endpoints
| Method | Endpoint           | Description                    |
|--------|--------------------|--------------------------------|
| POST   | `/albums`          | Create a new album            |
| GET    | `/albums`          | Get all albums for a user     |
| DELETE | `/albums/{id}`     | Delete an album by ID         |

### Image Endpoints
| Method | Endpoint           | Description                                               |
|--------|--------------------|-----------------------------------------------------------|
| POST   | `/images`          | Upload an image (Base64 format) to a specific album       |
| GET    | `/images`          | Get all images for a user with album filtering           |
| GET    | `/images/{id}`     | Get details of a specific image by ID                    |
| DELETE | `/images/{id}`     | Delete an image by ID                                     |

## Database Structure

### Users Table
| Column   | Type    | Description                     |
|----------|---------|---------------------------------|
| `id`     | UUID    | Primary key                    |
| `username` | VARCHAR | Unique username               |
| `password` | VARCHAR | Hashed password               |
| `email`    | VARCHAR | Unique email address          |

### Albums Table
| Column     | Type    | Description                      |
|------------|---------|----------------------------------|
| `id`       | UUID    | Primary key                     |
| `name`     | VARCHAR | Album name                      |
| `user_id`  | UUID    | Foreign key (User's ID)         |
| `created_at` | TIMESTAMP | Album creation date         |

### Images Table
| Column        | Type    | Description                           |
|---------------|---------|---------------------------------------|
| `id`          | UUID    | Primary key                          |
| `file_name`   | VARCHAR | Name of the image file               |
| `base64_data` | TEXT    | Base64 encoded image data            |
| `size`        | BIGINT  | Image file size in bytes             |
| `upload_date` | TIMESTAMP | Date when the image was uploaded   |
| `album_id`    | UUID    | Foreign key (Album's ID)             |

## Installation and Usage

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/azarft/ImageAPI.git
   cd ImageAPI

