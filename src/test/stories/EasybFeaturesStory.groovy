before "should execute before all scenarii", { println "should execute before all scenarii"}

scenario "a multiply b should give us (a += a) b times ", {
 def result
 when "#a * #b", {
     result = a * b 
 }
 then "result = ${result}", {
     result.shouldEqual a * b
 }
 where "a should be", {
     a = [1,2,3]
     b = [1,2,3]
   }
}
    