(ns moduck.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [moduck.core-test]))

(doo-tests 'moduck.core-test)
