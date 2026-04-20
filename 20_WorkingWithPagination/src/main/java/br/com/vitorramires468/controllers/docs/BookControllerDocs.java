package br.com.vitorramires468.controllers.docs;

import br.com.vitorramires468.data.dto.BookDTO;
import br.com.vitorramires468.model.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BookControllerDocs {

    @Operation(
            summary = "find a book",
            description = "finds a book by its id ",
            tags = {"Book"},
             responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Book.class))
                            }
                    ),
                     @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                     @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                     @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                     @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
             }
    )
    BookDTO findById(@PathVariable Long id);

    @Operation(
            summary = "finds all books",
            description = "finds all books ",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Book.class))
                            }
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<PagedModel<EntityModel<BookDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );


    @Operation(
            summary = "create a book",
            description = "creates a book ",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Book.class))
                            }
                    ),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            }
    )
    BookDTO create(@RequestBody BookDTO bookDTO);

    @Operation(
            summary = "update a book",
            description = "update a book ",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Book.class))
                            }
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            }
    )
    BookDTO update(@RequestBody BookDTO bookDTO);

    @Operation(
            summary = "disable a book",
            description = "disable a specific book by its id",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Book.class))
                            }
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            }
    )
    BookDTO disableBook(@PathVariable Long id);

    @Operation(
            summary = "delete a book",
            description = "delete a book by its id ",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204",
                            content = {@Content}
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<?> deleteById(@PathVariable Long id);
}
