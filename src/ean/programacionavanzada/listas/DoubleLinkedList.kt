/**
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad EAN (Bogotá - Colombia)
 * Departamento de Sistemas
 * Faculta de Ingeniería
 *
 * Proyecto EAN Kotlin Collections
 * @author Universidad EAN
 * Fecha: Mar 12, 2018
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package ean.programacionavanzada.listas

/**
 * Implementación del tipo abstracto de datos lista, especificado en la interface IList, utilizando nodos doblemente
 * encadenados. Este esquema dinámico es mucho mejor que con los vectores.
 */
open class DoubleLinkedList<T> : IList<T> {
    //---------------------------------------------------------
    // Atributos
    //---------------------------------------------------------

    // La cabeza de la lista
    private var primero: Node<T>? = null

    // La cola de la lista
    private var ultimo: Node<T>? = null

    // El número de elementos en la lista
    private var tam: Int = 0

    //---------------------------------------------------------
    // Constructor
    //---------------------------------------------------------
    constructor()

    //---------------------------------------------------------
    // Métodos
    //---------------------------------------------------------

    /**
     * Retorna `true` si la lista está vacía (no contiene elementos), `false` si tiene al menos un elemento
     */
    override val isEmpty: Boolean
        get() = tam == 0
    /**
     * Retona el número de elementos que hacen parte de la lista
     */
    override val size: Int
        get() = tam

    /**
     * Agrega un elemento al final de la lista
     */
    override fun add(element: T) {
        var nodo = Node(element)
        if (isEmpty) {
            primero = nodo
            ultimo = nodo
        } else {
            ultimo?.next = nodo
            nodo.prev = ultimo
            ultimo = nodo
        }
        tam++
    }

    /**
     * Agrega un nuevo elemento al principio de la lista
     */
    override fun addToHead(element: T) {
        var nodo = Node(element)
        if (isEmpty) {
            primero = nodo
            ultimo = nodo
        } else {
            nodo.next = primero
            primero!!.prev = nodo
            primero = nodo
        }
        tam++
    }

    /**
     * Agrega un elemento en la posición específica de la lista
     */
    override fun add(position: Int, element: T) {
        require(position in 0..tam)
        if (position == 0) {
            addToHead(element)
        } else if (position == tam) {
            add(element)
        } else {
            var nodo = Node(element)
            var p = primero
            var i = 0
            while (i < position) {
                p = p!!.next
                i++
            }
            var q = p!!.prev
            p!!.prev = nodo
            q!!.next = nodo
            nodo.prev = q
            nodo.next = p
            tam++
        }
    }

    /**
     * Elimina el elemento que se encuentra en la posición indicada
     */
    override fun remove(position: Int) {
        require(!isEmpty)
        var nodo = primero
        if (position == 0) {
            nodo!!.next!!.prev = null
            tam--
        } else {
            for (i in 0 until tam) {
                if (i == position) {
                    nodo!!.prev!!.next = nodo.next
                    nodo!!.next!!.prev = nodo.prev
                    tam--
                }
                nodo = nodo!!.next
            }
        }
    }

    /**
     * Elimina el primer elemento de la lista
     */
    override fun removeFirst() {
        remove(0)
    }

    /**
     * Elimina el último elemento de la lista
     */
    override fun removeLast() {
        var nodo = primero
        for (i in 0 until tam) {
            if (nodo!!.next == null) {
                nodo!!.prev!!.next = null
            }
        }
        tam--
    }

    /**
     * Elimina la primera ocurrencia del elemento especificado
     */
    override fun removeElement(element: T) {
        var nodo = primero
        for (i in 0 until tam) {
            if (nodo!!.info == element && nodo!!.next != null) {
                nodo.next!!.prev = nodo.prev
                nodo.prev!!.next = nodo.next
            } else if (nodo!!.info == element && nodo!!.next == null) {
                removeLast()
            }
        }
    }

    /**
     * Retorna el elemento que se encuentra una determinada posición de la lista
     */
    override fun get(position: Int): T {
        require(position in 0 until tam)
        var p = primero
        var i = 0
        while (i < position) {
            p = p!!.next
            i++
        }
        return p!!.info
    }

    /**
     * Retorna el elemento que se encuentra en la primera posición de la lista
     *
     */
    override fun head(): T {
        require(!isEmpty)
        return primero!!.info
    }

    /**
     * Retorna la lista sin el primer elemento. Es una copia de esta lista, no modifica la lista que recibe el
     * mensaje correspondiente
     */
    override fun tail(): IList<T> {
        var lista2 = copy()
        lista2.removeFirst()
        return lista2
    }

    /**
     * Retorna el índice de la primer ocurrencia del elemento especificado en la lista, o -1 si el elemento dado no
     * existe en la lista
     */
    override fun indexOf(element: T): Int {
        var nodo = primero
        var resultado = 0
        for (i in 0 until tam) {
            if (nodo!!.info == element) {
                resultado = i
                break;
            } else if (nodo.next == null) {
                resultado = -1
            } else {
                nodo = nodo!!.next
            }
        }
        return resultado
    }

    /**
     * Retorna el índice de la última ocurrencia del elemento dado en la lista, o -1 si el elemento dado no existe en
     * la lista
     */
    override fun lastIndexOf(element: T): Int {
        var nodo = ultimo
        var resultado = 0
        for (i in tam-1 downTo 0) {
            if (nodo!!.info == element) {
                resultado = i
                break;
            } else if (nodo.prev == null) {
                resultado = -1
            } else {
                nodo = nodo!!.prev
            }
        }
        return resultado
    }


/**
 * Reemplaza el elemento en la posición especificada en la lista por elemento dado
 *
 */
override fun set(position: Int, element: T) {
    var nodo = primero
    for (i in 0 until tam) {
        if (i == position) {
            nodo!!.info = element
        } else {
            nodo = nodo!!.next
        }
    }
}

/**
 * Obtiene una copia idéntica de esta lista.
 */
override fun copy(): IList<T> {
    var lista: IList<T>? = null
    var nodo = primero
    for (i in 0 until tam){
        lista!!.add(nodo!!.info)
        nodo = nodo!!.next
    }
    return lista!!
}

//------------------------------------------------------------------------------------------------------------------

//-------------------------------------------------
// Esta clase implementa las operaciones de un nodo
// de la lista doblemente encadenada
//-------------------------------------------------
private inner class Node<T>(theInfo: T) {
    // Atributos
    var info: T = theInfo
    var next: Node<T>? = null
    var prev: Node<T>? = null
}

/**
 * Retorna un iterador sobre los elementos de esta secuencia de elementos
 */
override fun iterator(): Iterator<T> {
    return object : Iterator<T> {
        // Atributos
        private var nodo = primero

        /**
         * Returns `true` if the iteration has more elements.
         */
        override fun hasNext(): Boolean = (nodo != null)

        /**
         * Returns the next element in the iteration.
         */
        override fun next(): T {
            val resp = nodo?.info
            nodo = nodo?.next
            return resp!!
        }
    }
}

/**
 * Elimina todos los elementos de esta lista
 */
override fun clear() {
    primero = null
    ultimo = null
    tam = 0
}

/**
 * Convierte a cadena el objeto actual
 */
override fun toString(): String {
    var res = StringBuffer("[")
    var p = primero
    var i = 0

    while (p != null) {
        res.append(p.info.toString())
        if (i != (tam - 1)) {
            res.append(", ")
        }
        i++
        p = p.next
    }
    res.append("]")

    return "LinkedList(tam=$tam, info=$res)"
}

/**
 * Convierte la lista a una cadena de caracteres, pero recorriendo la lista del último al primer nodo
 */
fun toReverseString(): String {
    var res = StringBuffer("[")
    var p = ultimo
    var i = 0

    while (p != null) {
        res.append(p.info.toString())
        if (i != (tam - 1)) {
            res.append(", ")
        }
        i++
        p = p.prev
    }
    res.append("]")

    return "LinkedListR(tam=$tam, info=$res)"
}

/**
 * Permite saber si una lista es igual a otra. Solo podemos comparar listas, sin importar la implementación
 */
override fun equals(other: Any?): Boolean {
    if (this === other) {
        return true
    }
    if (other !is IList<*>) {
        return false
    }

    other as IList<T>

    if (this.size != other.size) {
        return false
    }

    var p = primero
    var i = 0

    while (p != null) {
        if (p.info != other[i]) {
            return false
        }
        i++
        p = p.next
    }

    return true
}

/**
 * Obtiene un código Hash para este objeto a partir del tamaño
 */
override fun hashCode(): Int {
    return tam
}
}

//----------------------------------------------------------------------------------------------------------------------

/**
 * Permite crear una lista a partir de un conjunto de elementos que se pasan como parámetros
 */
fun <E> asLinkedList(vararg elements: E): DoubleLinkedList<E> {
    var result = DoubleLinkedList<E>()
    for (elem: E in elements) {
        result.add(elem)
    }
    return result
}

