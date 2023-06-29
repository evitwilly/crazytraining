package ru.freeit.crazytraining.training.viewmodel_states

class TrainingListState(val items: List<TrainingDetailState>) {

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is TrainingListState) return false

        return items == other.items
    }

    override fun hashCode(): Int = items.hashCode()

}