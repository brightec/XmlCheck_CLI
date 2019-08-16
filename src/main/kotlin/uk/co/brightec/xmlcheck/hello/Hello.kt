package uk.co.brightec.xmlcheck.hello

import com.github.ajalt.clikt.core.CliktCommand

class Hello: CliktCommand() {

    override fun run() {
        echo("Hello world!")
    }
}