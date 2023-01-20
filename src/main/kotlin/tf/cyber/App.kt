package tf.cyber

import io.kvision.*
import io.kvision.html.p
import io.kvision.html.span
import io.kvision.panel.*
import io.kvision.state.bindEach
import io.kvision.state.observableListOf
import io.kvision.table.cell
import io.kvision.table.row
import io.kvision.table.table
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// The list initially contains three entries.
// After a while, the last two entries (Entry 2 and Entry 3) are removed.
// Then, to other entries are added to the list (Entry 4 and Entry 5).
val demoList = observableListOf("ENTRY 1", "ENTRY 2", "ENTRY 3")

class App : Application() {
    override fun start(state: Map<String, Any>) {
        root("app", ContainerType.FLUID) {
            table(listOf("Column 1")) {
                caption = "Example Table"

                bindEach(demoList) {
                    row {
                        cell {
                            span(it)
                        }
                    }

                    // The following block works flawelessly on the other hand.
                    // Previous Entries 2 and 3 are removed.
                    /*p {
                        p {
                            p {
                                span(it)
                            }
                        }
                    }*/
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            demoList.remove("ENTRY 3")
            delay(500)
            demoList.remove("ENTRY 2")
            delay(500)
            demoList.add("ENTRY 4")
            demoList.add("ENTRY 5")
        }
    }

    override fun dispose(): Map<String, Any> {
        return mapOf()
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        CoreModule,
        BootstrapModule,
        BootstrapCssModule
    )
}
