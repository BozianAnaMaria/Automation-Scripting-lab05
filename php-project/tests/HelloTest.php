<?php
use PHPUnit\Framework\TestCase;
use Lab05\PhpProject\Hello;


final class HelloTest extends TestCase {
    public function testWorld() {
        $this->assertSame('Hello, world!', Hello::world());
    }
}