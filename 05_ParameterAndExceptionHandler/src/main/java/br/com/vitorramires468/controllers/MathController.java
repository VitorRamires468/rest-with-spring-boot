package br.com.vitorramires468.controllers;

import br.com.vitorramires468.exceptions.UnsupportedMathOperationException;
import br.com.vitorramires468.math.SimpleMath;
import br.com.vitorramires468.requestConverters.NumberConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    NumberConverter converter = new NumberConverter();
    SimpleMath math = new SimpleMath();

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo) throws IllegalArgumentException
    {
        if(!converter.isNumeric(numberOne) || !converter.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value");
        return math.sum(converter.convertToDouble(numberOne), converter.convertToDouble(numberTwo));
    }

    @GetMapping("/subtraction/{numberOne}/{numberTwo}")
    public Double subtraction(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo) throws IllegalArgumentException
    {
        if(!converter.isNumeric(numberOne) || !converter.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value");
        return math.subtraction(converter.convertToDouble(numberOne), converter.convertToDouble(numberTwo));
    }

    @GetMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo) throws IllegalArgumentException
    {
        if(!converter.isNumeric(numberOne) || !converter.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value");
        return math.multiplication(converter.convertToDouble(numberOne), converter.convertToDouble(numberTwo));
    }

    @GetMapping("/division/{numberOne}/{numberTwo}")
    public Double division(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo) throws IllegalArgumentException
    {
        if(!converter.isNumeric(numberOne) || !converter.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value");
        return math.division(converter.convertToDouble(numberOne), converter.convertToDouble(numberTwo));
    }

    @GetMapping("/mean/{numberOne}/{numberTwo}")
    public Double mean(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo) throws IllegalArgumentException
    {
        if(!converter.isNumeric(numberOne) || !converter.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value");
        return math.mean(converter.convertToDouble(numberOne), converter.convertToDouble(numberTwo));
    }
    @GetMapping("/squareroot/{numberOne}")
    public Double squareroot(
            @PathVariable("numberOne") String numberOne)

    {
        if(!converter.isNumeric(numberOne) ) throw new UnsupportedMathOperationException("Please set a numeric value");
        return math.squareRoot(converter.convertToDouble(numberOne));
    }


}
