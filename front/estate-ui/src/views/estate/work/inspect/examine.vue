<template>
  <div class="app-container">
    <WorkOrderAudit :orderType="orderType"
                    :adoptPermission="adoptPermission"
                    :rejectPermission="rejectPermission"
    ></WorkOrderAudit>


  </div>
</template>

<script>
import WorkOrderAudit from "@/components/work/workOrderAudit";

export default {
  components: {
    WorkOrderAudit
  },
  data() {
    return {
      orderType: "2",
      adoptPermission: "inspect:audit:adopt",
      rejectPermission: "inspect:audit:reject"
    };
  }
};
</script>
<style scoped>
</style>
