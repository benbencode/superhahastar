### Flink状态管理与恢复机制

#### Keyed State的数据结构类型有:
ValueState<T>:update(T)
ListState<T>:add(T)、get(T)和clear(T)
ReducingState<T>:add(T)、reduceFunction()
MapState<UK,UV>:put(UK,UV)、putAll(Map<UK,UV>)、get(UK)



注意：使用状态，必须使用RichFunction，因为状态是使用RuntimeContext访问的，只能在RichFunction中访问




### transient 关键字







